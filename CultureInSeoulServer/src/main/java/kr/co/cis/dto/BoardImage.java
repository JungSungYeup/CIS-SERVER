package kr.co.cis.dto;

public class BoardImage {
	private Long id;
	private String ori_name;
	private String save_name;
	private Long board_id;
	
	public BoardImage() {
		
	}
	public BoardImage(Long id, String ori_name, String save_name, Long board_id) {
		super();
		this.id = id;
		this.ori_name = ori_name;
		this.save_name = save_name;
		this.board_id = board_id;
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
	public Long getBoard_id() {
		return board_id;
	}
	public void setBoard_id(Long board_id) {
		this.board_id = board_id;
	}
	
	

}
