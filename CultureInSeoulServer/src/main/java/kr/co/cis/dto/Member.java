package kr.co.cis.dto;

public class Member {
	private Long id;
	private String member_id;
	private String member_pw;
	private String member_nickname;
	
	public Member() {
		
	}
	
	public Member(Long id, String member_id, String member_pw, String member_nickname) {
		super();
		this.id = id;
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_nickname = member_nickname;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getMember_nickname() {
		return member_nickname;
	}
	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}
}
