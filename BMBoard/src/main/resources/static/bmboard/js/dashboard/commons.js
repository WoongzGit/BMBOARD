/* 회원가입 버튼 */
$(document).on("click", '.memberSingUp', function(){
	var _parent = $(this).parent().parent();
	
	if(_parent.find(".form-group:eq(2)").attr("style") != undefined &&
		_parent.find(".form-group:eq(2)").attr("style") != null &&
		_parent.find(".form-group:eq(2)").attr("style") != ""){
		_parent.find(".form-group:eq(2)").removeAttr("style");
		
		return false;
	}
	
	if(!confirm("가입하시겠습니까?")){
		_parent.find("#formEmail").val("");
		_parent.find("#formPassword").val("");
		return false;
	}
	
	var url = "/bmboard/user/";
	var type = "POST";
	var data = {
		  _csrf:$('#csrf').val()
		, email:_parent.find("#formEmail").val()
		, postIdx:_parent.find("#formPassword").val()
		, name:_parent.find("#formName").val()
	};
	var onParam = {
		_parent:_parent
	};
	
	getList(url, type, data, memberSingUp, onParam);
});

/* 회원가입 버튼 콜백 */
var memberSingUp = function (data, onParam) {
	if(data.resultVo.code == "0000"){
		$('#dialogComplete #msg').html("가입됐습니다. 로그인해주세요.");
		$('#dialogComplete').modal('show');
		onParam._parent.find("#formEmail").val("");
		onParam._parent.find("#formPassword").val("");
		onParam._parent.find("#formName").val("");
		onParam._parent.find(".form-group:eq(2)").attr("style", "display:none;");
	}else{
		$('#dialogAlert #msg').html(data.resultVo.msg);
		$('#dialogAlert').modal('show');
	}
}

$(document).on("click", '.memberLogIn', function(){
	$('#memberForm #username').val($('#formEmail').val());
	$('#memberForm #password').val($('#formPassword').val());
	$('#memberForm').attr("action", "/bmboard/loginProcess");
	$('#memberForm').submit();
});

$(document).on("click", '#viewExplain', function(){
	$('#dialogExplain').modal('show');
});

var stringByteLength = (function(s,b,i,c){
    for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
    return b
});

/* 데이터 조회
   url : url
   type : method 타입
   data : 조회 데이터
   onSuccess : 성공 시 수행 메소드
   onParam : 성공 시 수행할 메소드에 전달할 파라미터
*/
var getList = function(url, type, data, onSuccess, onParam){
	$.ajax({
	      url:url
	    , async:true
	    , type:type
	    , data:data
	    , dataType:'json'
	    , success:function(e) {
	    	data = e;
	    	
	    	onSuccess(data, onParam);
	    	
	    	console.log(data);
	    }
	    , error:function(e) {
	    	console.log(e);
	    }
	});
}

/* 사용자 컴포넌트 생성
   email : 사용자 이메일
   ranking : 사용자 랭킹
*/
var getUserComp = function(_html, email, ranking){
	var feather = "";
	var html = _html;
	console.log("getUserComp : " + email + "\t" + ranking + "\t" + html);
	switch(ranking){
	case 1 : feather = "chrome"; break;
	case 9 : feather = "compass"; break;
	case 20 : feather = "plus-circle"; break;
	default : feather = "circle"; break;
	}
	
	html = html.replace("!feather!", feather);
	html = html.replace("!email!", email);
	console.log("getUserComp : " + email + "\t" + ranking + "\t" + html);
	
	return html;
}

/* 사용자 등급에 해당하는 feather 값 리턴
   ranking : 사용자 등급
*/
var getUserFeather = function(ranking){
	var feather = "";
	switch(ranking){
	case 1 : feather = "chrome"; break;
	case 9 : feather = "compass"; break;
	case 20 : feather = "plus-circle"; break;
	default : feather = "circle"; break;
	}
	
	return feather;
}

/* 댓글 컴포넌트 생성
   data : 페이징 처리된 데이터
   isReload : 해당 페이지 삭제 여부
*/
var getCommentCompList = function(data, isReload){
	if(isReload){
		$('#commentList .reload').remove();
	}
	
	if(!data.first){
		$('#commentList .reload').removeClass("reload");
		$('#commentList').children().last().remove();
	}
	
	$('#commentList').append(getCommentHtml(data));
}

/* 댓글 컴포넌트 생성
   data : 페이징 처리된 데이터
*/
var getCommentHtml = function(data){
	var feather = "";
	var html = "";
	var comment = null;
	for(var i = 0; i < data.numberOfElements; i++){
		comment = data.content[i];
		console.log(i);
		console.log(comment);
		html += '<div class="col-9 border-top reload">';
		html += getUserComp(comment.memberEntity.email, comment.memberEntity.ranking);
		html += '<p class="mt-3 text-justify" style="word-break:break-all;width:100%;">' + comment.commentContents + '</p>';
		html += '</div>';
		html += '<div class="col-3 border-top reload">';
		html += '<p class="text-right mt-3">';
		html += '<span>' + comment.regDate.substr(0, 10) + " " + comment.regDate.substr(11, 8) + '</span><br>';
		if(comment.commentState == "NORMAL"){
			html += '<button type="button" id="buttonComDelete" class="btn btn-danger btn-sm mt-3 hide" data="' + comment.commentIdx + '">';
			html += '<span data-feather="trash"></span>';
			html += '</button>';
		}else if(comment.commentState == "BLOCK"){
			html += '<button type="button" id="buttonComDelete" class="btn btn-warning btn-sm mt-3 show" data="' + comment.commentIdx + '">';
			html += '<span data-feather="trash"></span>';
			html += '</button>';
		}else if(comment.commentState == "DELETE"){
			html += '삭제된 댓글';
		}
		html += '</p>';
		html += '</div>';
	}
	
	html += '<div class="col-12 border-top">'
	html += '<p class="text-center"><a href="javascript:void(0);" id="moreComment">'
	html += '<span class="" data-feather="plus-circle" id="rankIcon1"></span><span> 더 보기</span>'
	html += '</a></p>'
	html += '</div>';
	
	return html;
}