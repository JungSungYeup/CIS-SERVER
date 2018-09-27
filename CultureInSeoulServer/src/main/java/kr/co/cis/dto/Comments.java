package kr.co.cis.dto;

public class Comments {
	private Long id;
	private String comments_contents;
	private String comments_nick;
	private String comments_time;
	private Long board_id;
	
	public Comments() {
		
	}
	
	public Comments(Long id, String comments_contents, String comments_nick, String comments_time, Long board_id) {
		super();
		this.id = id;
		this.comments_contents = comments_contents;
		this.comments_nick = comments_nick;
		this.comments_time = comments_time;
		this.board_id = board_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComments_contents() {
		return comments_contents;
	}
	public void setComments_contents(String comments_contents) {
		this.comments_contents = comments_contents;
	}
	public String getComments_nick() {
		return comments_nick;
	}
	public void setComments_nick(String comments_nick) {
		this.comments_nick = comments_nick;
	}
	public String getComments_time() {
		return comments_time;
	}
	public void setComments_time(String comments_time) {
		this.comments_time = comments_time;
	}
	public Long getBoard_id() {
		return board_id;
	}
	public void setBoard_id(Long board_id) {
		this.board_id = board_id;
	}
	
	

}
