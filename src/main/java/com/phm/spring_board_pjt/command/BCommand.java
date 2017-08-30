package com.phm.spring_board_pjt.command;

import org.springframework.ui.Model;

/*
 * 커맨드 패키지 아래의 클래스들은 일종의 서비스부라고 보면 된다. 
 */

public interface BCommand {
	
	public void execute(Model model);
}
