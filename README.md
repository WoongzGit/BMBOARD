# BMBOARD
게시판

사용자 프로그램 readme
bmadmin의 readme를 다 실행 한 후 다음을 읽고 따라 실행해주세요.

1. 환경 설정
	- java version : 11.0.9
	- db(h2) version : 1.4.199
	- 메이븐 : 4.0.0
	- thymleaf

2. 메일 인증 과정
	1. gmail 계정을 생성한다.
	2. 구글 접속 후 로그인
	3. 구글 앱 리스트에서 'google 계정'(화면 우측 상단에서 두번째 > 나타난 메뉴의 좌측 상단에서 첫번째) 클릭
	4. '보안' > '보안 수준이 낮은 앱의 액세스' 를 '사용'으로 선택
	5. bmboard/src/main/resources/application.properties 파일 오픈
	6. spring.mail.user에 gmail 계정, spring.mail.password에 gmail 계정 비밀번호 입력
	
3. maven 빌드 goal : clean package
	
4. 실행 순서
	1. bmadmin 메이븐 빌드(goal : clean package)
	2. java jar 파일 실행
	3. localhost:8080 접속
	
5. 비기능적 요구사항
	1. 소스 코드를 여러 명이 사용할 때 어떻게 관리할 것인가
		- 파트가 나눠져 접근해야 하는 파일의 구분이 명확한 경우, 해당 기능에 대한 수정을 본인이 하지 않고 해당 기능을 수행하는 파일을 담당하는 파트에 해당 기능을 요청한다.
		  하지만 일을 하면서 느낀 점은 그렇게 해도 그 팀 역시 바쁘기 때문에 내 요청사항이 빠르게 반영되기는 쉽지 않은 것이 현실이다.
		  그래서 나의 경우 로컬에서 해당 파일에 내가 원하는 기능을 생성하고 테스트 해본 후 해당 내용을 첨부해 요청했었다.
		  
	2. 타 팀에서 해당 프로젝트의 API를 사용할 때의 용이성
		- 현재 구성한 프로그램에서는 페이지에 접근하고 해당 페이지를 구성하는데 필요한 일부 기능을 제외하고는 restful로 개발하려고 했다.
		  따라서 적절한 조회(method와 url)를 이용할 경우 필요한 정보를 얻을 수 있다.
		  GET method 이외의 값을 요청할 경우 spring security에서 사용하는 csrf값을 파라미터에 추가해 보내야 한다.
		  사용 가능한 url과 method는 엑셀에 정리되있다.
	
	3. 보안
		- 관리자 기능에서는 로그인 페이지와 로그인 수행 url을 제외한 다른 기능은 ROLE_MEMBER 권한이 있어야 접근 가능하도록 했다.
		  그 외 XSS 공격 방지를 위해 html필터를 사용했다.
		  원래 게시글 작성 시 summernote 에디터를 사용하고 싶었지만 html태그 모두를 필터 걸기 때문에 지금은 적용하지 못했다.
		  하지만 추후 html필터링 방식을 화이트리스트 방식으로 변경한다면 summernote를 이용한 게시글 작성이 가능할 것 같다.
		  
		  ssl을 적용하지 않았기에 비밀번호를 입력할 경우 화면에서 BCrypt를 하고 싶었지만 시간관계상 개발도, 마땅한 라이브러리를 찾지도 못했다.
		  추후 ssl을 적용하고 화면에서 비밀번호를 BCrypt해 서버로 전송, 서버에서는 저장된 BCrypt된 비밀번호와 비교만 하도록 수정하는 것이 좋을 것 같다.
		  
		  서버 오류 발생 시 페이지는 사용자측에서 확인할 수 없도록 같은 내용을 가진 다른 파일들로 이동하도록 설정했다.
		  
		  관리자에서도 이야기 했듯 사용자 비밀번호 초기화 로직이 추가되는 것이 좋을 것 같다.
		  또한 사용자가 자신의 정보를 확인, 수정, 비밀번호 초기화를할 수 있게 mypage기능을 추가하면 좋을 것 같다.
		  
		  이메일과 이름을 받기 때문에 개인정보보호법에서 정한 관리(주기적인 삭제 및 연단위 휴면 관리)를 해야할 가능성이 있다. (2012.2월 개인정보보호법 상담사례집 참고, 2018년/2019년 버전에서는 찾지 못함)
		  
	4. 코드의 품질과 버그를 관리할 수 있는 방안
		- 되도록 내용을 엑셀로 정리하며 하고 싶었지만 시간관계상 모든 내용을 정리하며 하지는 못했다.
		  코드의 품질 경우 junit을 이용해 테스트를 하면 좋겠지만 이역시 이번에는 적용하지 못했다.
		  
		  버그 관리의 경우 사용자의 접근 url이나 호출시 파라미터, 성공 실패 여부 등등 에러를 추적할 수 있는 프로세스를 적용하면 좋을 것 같다.
		  특히 url과 호출 파라미터를 조합할 경우 상당부분의 에러 발생 시 큰 도움이 될 것 같다.
