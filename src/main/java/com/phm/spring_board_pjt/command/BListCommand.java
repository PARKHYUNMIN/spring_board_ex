package com.phm.spring_board_pjt.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.phm.spring_board_pjt.dao.BDao;
import com.phm.spring_board_pjt.dto.BDto;

public class BListCommand implements BCommand{

	@Override
	public void execute(Model model) {
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list(); // 게시글들 가져와서 ArrayList에 담
		model.addAttribute("list", dtos);
	}
}
