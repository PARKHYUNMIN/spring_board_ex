package com.phm.spring_board_pjt.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.phm.spring_board_pjt.dao.BDao;
import com.phm.spring_board_pjt.dto.BDto;

public class BContentCommand implements BCommand{
	// 게시판에서 클릭한 글의 내용을 bId를 통해 가져온다. 
	
	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		
		BDao dao = new BDao(); // Data Access 객체 생성 
		BDto dto = dao.contentView(bId); // dto 객체를 리턴하는 contentView method 호출 
		
		model.addAttribute("content_view", dto); // dto 객체를 모델에 더해주기 
	}

}
