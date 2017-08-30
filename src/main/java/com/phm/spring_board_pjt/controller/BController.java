package com.phm.spring_board_pjt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.phm.spring_board_pjt.command.BCommand;
import com.phm.spring_board_pjt.command.BContentCommand;
import com.phm.spring_board_pjt.command.BDeleteCommand;
import com.phm.spring_board_pjt.command.BListCommand;
import com.phm.spring_board_pjt.command.BModifyCommand;
import com.phm.spring_board_pjt.command.BReplyCommand;
import com.phm.spring_board_pjt.command.BReplyViewCommand;
import com.phm.spring_board_pjt.command.BWriteCommand;

@Controller
public class BController {
	// requestMapping을 통해 command들을 실행하게 될 부분
	// 클라이언트 -> 디스패쳐 -> 컨트롤러
	// 디스패쳐에서 컨트롤러를 찾는 것은 servlet-context.xml의 component-scan tag를 참조하여 스캔하게된다.
	// @컨트롤러 어노테이션이 있는 것을 찾아간다.
	BCommand command;

	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list()");

		command = new BListCommand();
		command.execute(model);

		return "list"; // -> list.jsp
	}

	// 작성 뷰 페이지로 단순 이동
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view()");

		return "write_view";
	}

	// 작성 method -> 작성 후 list page로 redirection
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		System.out.println("write()");

		model.addAttribute("request", request);
		command = new BWriteCommand();
		command.execute(model);

		return "redirect:list";
	}

	// 작성 글 내 보기
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");

		model.addAttribute("request", request);
		command = new BContentCommand();
		command.execute(model);

		return "content_view";
	}

	// 수정 메소드
	@RequestMapping(method = RequestMethod.POST, value = "/modify")
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("modify()");

		model.addAttribute("request", request);
		command = new BModifyCommand();
		command.execute(model);

		return "redirect:list";
	}

	// 답변 확인
	@RequestMapping("/reply_view")
	public String replyView(HttpServletRequest request, Model model) {
		System.out.println("replyView()");

		model.addAttribute("request", request);
		command = new BReplyViewCommand();
		command.execute(model);

		return "reply_view";
	}
	
	// 답변하기 
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println();
		
		model.addAttribute("request",request);
		command = new BReplyCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	// 삭제하기
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println();
		
		model.addAttribute("request",request);
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
}
