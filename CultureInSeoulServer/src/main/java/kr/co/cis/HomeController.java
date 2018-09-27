package kr.co.cis;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import kr.co.cis.dao.iDao;
import kr.co.cis.dto.Favorite;
import kr.co.cis.dto.Member;
import kr.co.cis.dto.MemberImage;
import kr.co.cis.dto.Board;
import kr.co.cis.dto.BoardImage;
import kr.co.cis.dto.Comments;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
	
		
		return "home";
	}
	
	
	//회원정보 DB에 저장하는 부분.
	@RequestMapping(value="info.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getMemberInfo(HttpServletRequest request) {
		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
		String member_nickname = request.getParameter("member_nickname");
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
		System.out.println(member_id +" " + member_pw + " " + member_nickname);
		iDAO.insertMember(member_id,member_pw,member_nickname);
		
		Gson gson = new Gson();
		
		return gson.toJson("");
	}
	
	
	//회원정보 아이디 중복 있나 확인하는 부분. 중복이 있으면 0, 없으면 1 이 리턴된다.
	@RequestMapping(value="id_ok.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String id_check(HttpServletRequest request) {
		String member_id = request.getParameter("member_id");
		Gson gson = new Gson();
		iDao iDAO = sqlSession.getMapper(iDao.class);
		ArrayList<Member> items = iDAO.getMemberList();
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getMember_id().equals(member_id)) {
				return gson.toJson(0);
			}
		}
		return gson.toJson(1);
	}
	
	//회원정보 닉네임 중복 있나 확인하는 부분.중복이 있으면 0, 없으면 1 이 리턴된다.
	@RequestMapping(value="nick_ok.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String nick_check(HttpServletRequest request) {
		String member_nickname = request.getParameter("member_nickname");
		Gson gson = new Gson();
		iDao iDAO = sqlSession.getMapper(iDao.class);
		ArrayList<Member> items = iDAO.getMemberList();
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getMember_nickname().equals(member_nickname)) {
				return gson.toJson(0);
			}
		}
		return gson.toJson(1);
	}
	
	//아이디 패스워드를 가져와 회원정보에 있는 아이디 패스워드와 비교해 아이디만 같으면 1, 아이디 비번 다 같으면 2, 아무것도 같지 않으면 0이 리턴된다.
	@RequestMapping(value="login_ok.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String login_ok(HttpServletRequest request) {
		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
		iDao iDAO = sqlSession.getMapper(iDao.class);
		Gson gson = new Gson();
		int count_id = iDAO.loginid(member_id);
		int count = iDAO.login(member_id, member_pw);
		
		if(count_id > 0) {
			if(count > 0) {
				return gson.toJson(count);
			} else {
				return gson.toJson(2);
			}
		}
		
		return gson.toJson(0);
	}
	
	//아이디에 해당되는 회원정보를 가져와 리턴해준다.
	@RequestMapping(value="getloginmember.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getloginmember(HttpServletRequest request) {
		String member_id = request.getParameter("member_id");
		iDao iDAO = sqlSession.getMapper(iDao.class);
		Member member = iDAO.getmemberinfo(member_id);
		
		System.out.println(member.getMember_id() +" " + member.getMember_pw() + " " + member.getMember_nickname());
		Gson gson = new Gson();
		
		return gson.toJson(member);
	}
	
	//즐겨찾기 정보를 가져와 DB에 넣어준다.
	@RequestMapping(value="setfavorite.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String setfavorite(HttpServletRequest request) {
		
		String code1 = request.getParameter("code");
		String member_id1 = request.getParameter("member_id");
		
		Integer code = Integer.parseInt(code1);
		Long member_id = Long.parseLong(member_id1); 
		
		System.out.println(code.toString() + " " + member_id.toString());
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
		
		ArrayList<Favorite> favorite = iDAO.getFavoriteList(member_id);
		int num = 0;
		for(int i = 0; i < favorite.size(); i++) {
			if(favorite.get(i).getCode().toString().equals(code1)) {
				num++;
			}
		}
		
		if(num == 0) {
			iDAO.insertfavorite(code, member_id);
		}
		
		
		
		Gson gson = new Gson();
		
		
		return gson.toJson("");
	}
	
	
	//즐겨찾기 정보를 가져와 리턴해준다.
	@RequestMapping(value="getfavorite.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getfavorite(HttpServletRequest request) {
		String member_id1 = request.getParameter("member_id");
		Long member_id = Long.parseLong(member_id1); 
		iDao iDAO = sqlSession.getMapper(iDao.class);
		ArrayList<Favorite> favorite = iDAO.getFavoriteList(member_id);
		Gson gson = new Gson();
		
		return gson.toJson(favorite);
	
	}
	
	//즐겨찾기 정보를 가져와 즐겨찾기 되있으면 1, 아니면 0 을 리턴해준다.
	@RequestMapping(value="isfavorite.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String isfavorite(HttpServletRequest request) {
		String code1 = request.getParameter("code");
		String member_id1 = request.getParameter("member_id");
		Integer code = Integer.parseInt(code1);
		Long member_id = Long.parseLong(member_id1); 
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
		ArrayList<Favorite> favorite = iDAO.getFavoriteList(member_id);
		int num = 0;
		for(int i = 0; i < favorite.size(); i++) {
			if(favorite.get(i).getCode().toString().equals(code1)) {
				num++;
			}
		}
		
		Gson gson = new Gson();
		
		if(num > 0) {
			return gson.toJson(1);
		} else {
			return gson.toJson(0);
		}
	
	}
	
	//즐겨찾기 정보를 DB에서 지운다.
	@RequestMapping(value="deletefavorite.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String delfavorite(HttpServletRequest request) {
		String code1 = request.getParameter("code");
		String member_id1 = request.getParameter("member_id");
		Integer code = Integer.parseInt(code1);
		Long member_id = Long.parseLong(member_id1); 
		iDao iDAO = sqlSession.getMapper(iDao.class);
		iDAO.deleteFavorite(code, member_id);
		
		Gson gson = new Gson();
		
		return gson.toJson("");
	}
	
	//회원정보 이미지를 DB에 저장한다.
	@RequestMapping(value="addmemberimage.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String addmemberimage(MultipartHttpServletRequest req,HttpServletRequest request) {
		MultipartFile uploadFile = req.getFile("asdfff");
		String member_id1 = request.getParameter("member_id");
		Long member_id = Long.parseLong(member_id1);
		String root_path = req.getSession().getServletContext().getRealPath("/");
		String attach_path = "resources/upload/";
		String path = root_path + attach_path;		
		String url = "";
		iDao iDAO = sqlSession.getMapper(iDao.class);
		
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req;
	
		Iterator<String> iter = mhsr.getFileNames();
		MultipartFile mfile = null;
		while (iter.hasNext()) {
			String fieldName = (String) iter.next();
			
			mfile = mhsr.getFile(fieldName);
			String origName;
			
			origName = mfile.getOriginalFilename();
			
			if (origName != null && !origName.equals("")) {
				String ext = origName.substring(origName.lastIndexOf('.')); // 확장자
				String save_name = getRandomString() + ext;
	
				File serverFile = new File(path + "/" + save_name);
				
				try {
					mfile.transferTo(serverFile);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Integer asd = iDAO.getmemberimagenum(member_id);
				System.out.println("asd : " + asd);
				if(asd==0) {
					iDAO.insertFile(origName, save_name,member_id);
				} else if (asd==1) {
					iDAO.updateMemberImage(origName ,save_name, member_id);
					System.out.println("origName : " + origName);
					System.out.println("sfile : " + save_name);
				}
			}
		}
		
		Gson gson = new Gson();
		return gson.toJson("");
	}
	
	//회원정보 이미지의 저장 이름을 랜덤으로 생성시켜준다.
	public String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
	
	
	//회원정보의 닉네임을 수정한다.
	@RequestMapping(value="renickname.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String renickname(HttpServletRequest request) {
		String id1 = request.getParameter("id");
		Long id = Long.parseLong(id1);
		String member_nickname = request.getParameter("member_nickname");
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
		iDAO.updatenickname(id, member_nickname);
		
		Gson gson = new Gson();
		
		return gson.toJson("");
	}
	
	//회원정보 이미지를 리턴해준다.
	@RequestMapping(value="getmemberimage.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getmemberimage(HttpServletRequest request) {
		String id1 = request.getParameter("member_id");
		Long member_id = Long.parseLong(id1);
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
		Integer asd = iDAO.getmemberimagenum(member_id);
		
		Gson gson = new Gson();
		if(asd==0) {
			return gson.toJson(null);
		} else {
			MemberImage image = iDAO.getmemberimage(member_id);
			return gson.toJson(image);
		}
	}
	
	//자유게시판 게시물정보를 DB에 넣는다.
	@RequestMapping(value="insertBoard.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String insertboard(HttpServletRequest request) {
		String board_title = request.getParameter("S_title");
		String board_contents = request.getParameter("S_boder");
		String board_nick = request.getParameter("S_nickname");

		iDao iDAO = sqlSession.getMapper(iDao.class);
		
				
		iDAO.insertBoard(board_title, board_contents, board_nick, 0);
		
		Gson gson = new Gson();

		return gson.toJson("");
	}

	

	//게시물 정보를 리턴해준다.
	@RequestMapping(value="getBoard.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getBoard(HttpServletRequest request) {
		iDao iDAO = sqlSession.getMapper(iDao.class);
		ArrayList<Board> boards =iDAO.getForumBoard();
		Gson gson = new Gson();
		return gson.toJson(boards);
	}
	
	//게시물의 댓글 정보를 DB에 넣는다.
	@RequestMapping(value="insertComments.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String insertcomments(HttpServletRequest request) {
		String comments_contents = request.getParameter("C_boder");
		String comments_nick = request.getParameter("C_nick");
		String comments_time = request.getParameter("C_clock");
		String board_id1 = request.getParameter("C_id");
		Long board_id = Long.parseLong(board_id1);
		

		iDao iDAO = sqlSession.getMapper(iDao.class);
		Integer comments_count = iDAO.getCommentsNum(board_id);
		iDAO.updateBoard(board_id, comments_count);
		
		System.out.println(comments_contents+" "+comments_nick+" "+comments_time+" "+ board_id);
		iDAO.insertComments(comments_contents, comments_nick , comments_time, board_id);
		
		Gson gson = new Gson();

		return gson.toJson("");
	}
	
	
	//게시물의 댓글정보를 리턴해준다.
	@RequestMapping(value="getComment.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getComment(HttpServletRequest request) {
		String board_id1 = request.getParameter("S_id");
		Long board_id = Long.parseLong(board_id1);
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
		
		ArrayList<Comments> comments = iDAO.getComment(board_id);
		
		Gson gson = new Gson();
		
		return gson.toJson(comments);
	}	
	
	//게시물의 이미지 정보를 DB에 넣는다.
	@RequestMapping(value="insertboardimage.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String insertBoardimage(MultipartHttpServletRequest req,HttpServletRequest request) {
		MultipartFile uploadFile = req.getFile("asdfff");
		String root_path = req.getSession().getServletContext().getRealPath("/");
		String attach_path = "resources/upload/";
		String path = root_path + attach_path;		
		String url = "";
		iDao iDAO = sqlSession.getMapper(iDao.class);
		
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req;
	
		Iterator<String> iter = mhsr.getFileNames();
		MultipartFile mfile = null;
		while (iter.hasNext()) {
			String fieldName = (String) iter.next();
			
			mfile = mhsr.getFile(fieldName);
			String origName;
			
			origName = mfile.getOriginalFilename();
			
			if (origName != null && !origName.equals("")) {
				String ext = origName.substring(origName.lastIndexOf('.')); // 확장자
				String save_name = getRandomString() + ext;
	
				File serverFile = new File(path + "/" + save_name);
				
				try {
					mfile.transferTo(serverFile);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			Long board_id = iDAO.getboard();
			iDAO.insertboardimage(origName, save_name, board_id);
					
			}
		}
		
		return "";
	}
	
	//게시물의 이미지 정보를 리턴해준다.
	@RequestMapping(value="getboardimage.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getboardimage(HttpServletRequest request) {
		String id1 = request.getParameter("board_id");
		Long board_id = Long.parseLong(id1);
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
			
			Integer asd = iDAO.getboardimagenum(board_id);
			
			Gson gson = new Gson();
			if(asd==0) {
				return gson.toJson(null);
			} else {
				BoardImage image = iDAO.getboardimage(board_id);
				return gson.toJson(image);
			}
		
	}
	
	//게시물의 이미지 정보가 있으면 1, 없으면 0을 리턴해준다.
	@RequestMapping(value="getimagenum.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String getboardimanum(HttpServletRequest request) {
		String id1 = request.getParameter("board_id");
		Long board_id = Long.parseLong(id1);
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
			
			Integer asd = iDAO.getboardimagenum(board_id);
			
			Gson gson = new Gson();
			if(asd==0) {
				return gson.toJson(0);
			} else {
				return gson.toJson(1);
			}
		
	}

	//댓글작성자 닉네임과 나의 닉네임을 비교해서 같으면 1을 리턴 다르면 0을리턴
	@RequestMapping(value="deleteVisible.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String deleteVisible(HttpServletRequest request) {
		String my_nick = request.getParameter("my_nick");
		String comments_nick = request.getParameter("comments_nick");
		

		iDao iDAO = sqlSession.getMapper(iDao.class);

		Gson gson = new Gson();
		
		if(my_nick.equals(comments_nick)) {
			return gson.toJson(1);
		} else {
			return gson.toJson(0);
		}
	}
	
	//아이디에 해당하는 댓글 삭제코드 실행
	//아이디에 해당하는 댓글 카운트 코드 실행
	@RequestMapping(value="deleteComments.do", produces = "application/json; charset=utf8" )
	@ResponseBody
	public String deleteComments(HttpServletRequest request) {
		String board_id1 = request.getParameter("board_id");
		Long board_id = Long.parseLong(board_id1);
		String comments_id1 = request.getParameter("comments_id");
		Long comments_id = Long.parseLong(comments_id1);

		
		System.out.println(comments_id.toString());
		
		iDao iDAO = sqlSession.getMapper(iDao.class);
		Integer comments_count = iDAO.getCommentsNum(board_id);
		iDAO.deleteComments(comments_id);
		iDAO.DeleteupdateBoard(board_id, comments_count);
		
		Gson gson = new Gson();
		
		return gson.toJson("");
	}
}
