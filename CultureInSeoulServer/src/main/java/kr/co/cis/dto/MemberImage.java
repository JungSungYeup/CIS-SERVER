package kr.co.cis.dto;

public class MemberImage {
	private Long id;
	private String ori_name;
	private String save_name;
	private Long member_id;
	
	public MemberImage() {
		
	}
	
	public MemberImage(Long id, String ori_name, String save_name, Long member_id) {
		super();
		this.id = id;
		this.ori_name = ori_name;
		this.save_name = save_name;
		this.member_id = member_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOri_name() {
		return ori_name;
	}
	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}
	public String getSave_name() {
		return save_name;
	}
	public void setSave_name(String save_name) {
		this.save_name = save_name;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	
	

}
