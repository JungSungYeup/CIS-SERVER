package kr.co.cis.dto;

public class Favorite {
	private Long id;
	private Integer code;
	private Long member_id;
	
	public Favorite() {
		
	}
	
	public Favorite(Long id, Integer code, Long member_id) {
		super();
		this.id = id;
		this.code = code;
		this.member_id = member_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	
	

}
