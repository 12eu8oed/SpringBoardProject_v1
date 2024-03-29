package com.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.ReplyVO;
import com.board.service.ReplyService;

@Controller
//@RequestMapping("/reply/*")
@RequestMapping("/reply/*")
public class ReplyController {

	@Inject
	private ReplyService replyService; // 서비스 객체 주입
	
	// 댓글 조회
	
	// 댓글 작성
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String postWrite(ReplyVO vo) throws Exception {
		
		replyService.write(vo);

		// 댓글을 작성했던 게시물로 다시 이동 
		// 동작순서 : 사용자가요청 => view.jsp에서 댓글 작성 => Controller => Service => DAO=> Mapper => DB 그후 조회중이였던 게시물로 이동
		return "redirect:/board/view?bno=" + vo.getBno();

	}
	
	// 댓글 단일 조회(수정 페이지)
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("bno") int bno, @RequestParam("rno")int rno, Model model) throws Exception {
		
		ReplyVO vo  = new ReplyVO();
		vo.setBno(bno);
		vo.setRno(rno);
		
		ReplyVO reply = replyService.replySelect(vo);
		
		model.addAttribute("reply", reply);
	}
	
	// 댓글 수정
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String postModify(ReplyVO vo) throws Exception {
		
		replyService.modify(vo); //service dao mapper 전달되어 수정
		
		return "redirect:/board/view?bno="+vo.getBno();
	}
	
	
	// 댓글 삭제
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("bno") int bno, @RequestParam("rno")int rno) throws Exception {
		ReplyVO vo  = new ReplyVO();
		vo.setBno(bno);
		vo.setRno(rno);
		
		replyService.delete(vo);
		return "redirect:/board/view?bno=" + vo.getBno();
	}

}
