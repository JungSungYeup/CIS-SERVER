package kr.co.cis.dao;

import java.util.ArrayList;

import kr.co.cis.dto.Member;
import kr.co.cis.dto.Favorite;
import kr.co.cis.dto.MemberImage;
import kr.co.cis.dto.BoardImage;
import kr.co.cis.dto.Board;
import kr.co.cis.dto.Comments;

public interface iDao {
	public void insertMember(String member_id, String member_pw, String member_nickname);
	public ArrayList<Member> getMemberList();
	public Integer login(String member_id, String member_pw);
	public Integer loginid(String member_id);
	public Member getmemberinfo(String member_id);
	public void insertfavorite(Integer code, Long member_id);
	public ArrayList<Favorite> getFavoriteList(Long member_id);
	public void deleteFavorite(Integer code, Long member_id);
	public void delCode(Long mamber_id);
	public void insertFile(String orgName,String saveName,Long member_id);
	public void updatenickname(Long id, String member_nickname);
	public Integer getmemberimagenum(Long member_id);
	public void updateMemberImage(String ori_name, String save_name, Long member_id);
	public MemberImage getmemberimage(Long member_id);
	
	public void insertBoard(String board_title,String board_contents,String board_nick, Integer Comments_count);
	public ArrayList<Board> getForumBoard();
	public void insertComments(String comments_contents, String comments_nick, String comments_time, Long board_id);
	public ArrayList<Comments> getComment(Long board_id);
	public Integer getCommentsNum(Long board_id);
	public void updateBoard(Long id, Integer Comments_count);
	
	public Long getboard();
	public void insertboardimage(String orgName,String saveName,Long board_id);
	public BoardImage getboardimage(Long board_id);
	public Integer getboardimagenum(Long board_id);
	

	public void DeleteupdateBoard(Long id, Integer Comments_count);
	public Integer deleteVisible(String my_nick, String comments_nick);
	public void deleteComments(Long comments_id);
}
