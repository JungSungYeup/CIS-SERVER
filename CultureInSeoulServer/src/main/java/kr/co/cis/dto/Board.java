package kr.co.cis.dto;

public class Board {
	private Long id;
	private String board_title;
	private String board_contents;
	private String board_nick;
	private Integer comments_count;
	
	public Board() {
		
	}
	public Board(Long id, String board_title, String board_contents, String board_nick, Integer comments_count) {
		super();
		this.id = id;
		this.board_title = board_title;
		this.board_contents = board_contents;
		this.board_nick = board_nick;
		this.comments_count = comments_count;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_contents() {
		return board_contents;
	}
	public void setBoard_contents(String board_contents) {
		this.board_contents = board_contents;
	}
	public String getBoard_nick() {
		return board_nick;
	}
	public void setBoard_nick(String board_nick) {
		this.board_nick = board_nick;
	}
	public Integer getComments_count() {
		return comments_count;
	}
	public void setComments_count(Integer comments_count) {
		this.comments_count = comments_count;
	}
	
	

}
