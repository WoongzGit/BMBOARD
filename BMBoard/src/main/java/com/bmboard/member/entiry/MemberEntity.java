package com.bmboard.member.entiry;

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
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "memberIdx", unique = true)
	private Long memberIdx;
	
	@Column(name = "email", unique = true, nullable=false)
	private String email;
	
	@Column(name = "password", nullable=false)
	private String password;
	
	@Column(name = "name", nullable=false)
	private String name;
	
	@Column(name = "auth", nullable=false)
	private String auth;
	
	public MemberEntity() {
		
	}
	
	public MemberEntity(Long memberIdx, String email, String password, String name, String auth) {
		this.memberIdx = memberIdx;
		this.email = email;
		this.password = password;
		this.name = name;
		this.auth = auth;
	}
	
	public String toString() {
		return "memberIdx : " + this.memberIdx + ", " + "email : " + this.email + ", " + "password : " + this.password + ", " + "name : " + this.name + ", " + "auth : " + this.auth;
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
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
