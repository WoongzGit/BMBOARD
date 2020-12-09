package com.bmboard.member.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="member")
public class MemberEntity implements UserDetails{
	/* 회원순번 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "memberIdx", unique = true)
	private Long memberIdx;

	/* 이름 */
	@Column(name = "name", nullable=false)
	private String name;
	
	/* 이메일 */
	@Column(name = "email", unique = true, nullable=false)
	private String email;
	
	/* 비밀번호 */
	@Column(name = "password", nullable=false)
	private String password;
	
	/* 권한 */
	@Column(name = "auth", nullable=false)
	private String auth;
	
	/* 랭킹 */
	@Column(name = "ranking", nullable=true)
	private Integer ranking;
	
	/* 등록일자 */
	@Column(name = "regDate", nullable=true)
	private LocalDateTime regDate;
	
	/* 수정일자 */
	@Column(name = "modDate", nullable=true)
	private LocalDateTime modDate;
	
	/* 회원계정상태 */
	@Column(name = "memberState", nullable=false)
	private String memberState;
	
	/* 관리자계정상태 */
	@Column(name = "adminState", nullable=false)
	private String adminState;
	
	/* 회원계정로그인시도횟수 */
	@Column(name = "memberTry", nullable=false)
	private Integer memberTry;
	
	/* 관리자계정로그인시도횟수 */
	@Column(name = "adminTry", nullable=false)
	private Integer adminTry;
	
	/* 등록 관리자 */
	@Column(name = "regAdmin", nullable=false)
	private String regAdmin;
	
	/* 수정 관리자 */
	@Column(name = "modAdmin", nullable=false)
	private String modAdmin;
	
	public MemberEntity() {
		
	}
	
	public MemberEntity(Long memberIdx, String name, String email, String password, 
						String auth, Integer ranking, LocalDateTime regDate,
						LocalDateTime modDate, String memberState, String adminState,
						Integer memberTry, Integer adminTry, String regAdmin, String modAdmin) {
		this.memberIdx = memberIdx;
		this.name = name;
		this.email = email;
		this.password = password;
		this.auth = auth;
		this.ranking = ranking;
		this.regDate = regDate;
		this.modDate = modDate;
		this.memberState = memberState;
		this.adminState = adminState;
		this.memberTry = memberTry;
		this.adminTry = adminTry;
		this.regAdmin = regAdmin;
		this.modAdmin = modAdmin;
	}

	public Long getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(Long memberIdx) {
		this.memberIdx = memberIdx;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<>();
	    for (String role : auth.split(",")) {
	      roles.add(new SimpleGrantedAuthority(role));
	    }
	    return roles;
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		boolean retObj = true;
		if("PWLOCK".equals(getAdminState())){
			retObj = false;
		}
		return retObj;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		boolean retObj = true;
		if("BLOCK".equals(getAdminState())){
			retObj = false;
		}
		return retObj;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public LocalDateTime getModDate() {
		return modDate;
	}

	public void setModDate(LocalDateTime modDate) {
		this.modDate = modDate;
	}

	public String getMemberState() {
		return memberState;
	}

	public void setMemberState(String memberState) {
		this.memberState = memberState;
	}

	public String getAdminState() {
		return adminState;
	}

	public void setAdminState(String adminState) {
		this.adminState = adminState;
	}

	public Integer getMemberTry() {
		return memberTry;
	}

	public void setMemberTry(Integer memberTry) {
		this.memberTry = memberTry;
	}

	public Integer getAdminTry() {
		return adminTry;
	}

	public void setAdminTry(Integer adminTry) {
		this.adminTry = adminTry;
	}

	public String getRegAdmin() {
		return regAdmin;
	}

	public void setRegAdmin(String regAdmin) {
		this.regAdmin = regAdmin;
	}

	public String getModAdmin() {
		return modAdmin;
	}

	public void setModAdmin(String modAdmin) {
		this.modAdmin = modAdmin;
	}
}
