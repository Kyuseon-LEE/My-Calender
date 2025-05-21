package com.office.calender.board;

import java.security.Principal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Log4j2
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping({"", "/"})
	public String home() {
		log.info("home()");
		
		String nextPage = "redirect:/board/list";
		
		return nextPage;
	}
	
	@GetMapping("/list")
	public String list(Model model, Principal principal) {
		log.info("list()");
		
		String nextPage = "board/list";
		Map<String, Object> resultMap = boardService.list(principal.getName());
		
		model.addAttribute("resultMap", resultMap);
		
		return nextPage;
	}
	
	@GetMapping("/write_form")
	public String writeForm() {
		log.info("writeForm()");
		
		String nextPage = "board/write_form";
		
		return nextPage;
	}
	
	@PostMapping("/write_confirm")
	public String wrtieConfirm(BoardDto boardDto, Principal principal) {
		log.info("wrtieConfirm()");
		
		String nextPage = "board/write_ok";
		
		boardDto.setM_id(principal.getName());
		
		int result = boardService.writeConfirm(boardDto);
		if(result <= 0)
			nextPage = "board/write_ng";
		return nextPage;
	}
	@GetMapping("/body")
	public String body(@RequestParam("b_no") int b_no, Model model) {
		log.info("body()");
		
		String nextPage = "board/body";
		
		BoardDto boardDto = boardService.body(b_no);
		
		model.addAttribute("boardDto",boardDto);
		
		return nextPage;
	}
	@GetMapping("reply_form")
	public String replyForm(@RequestParam("b_no") int b_no, Model model ) {
		log.info("replyForm()");
		
		String nextPage="board/reply_form";
		
		BoardDto boardDto = boardService.replyForm(b_no);
		
		model.addAttribute("boardDto", boardDto);
		
		return nextPage;
	}
	
	@PostMapping("/reply_confirm")
	public String replyConfirm(BoardDto boardDto, Principal principal) {
		log.info("replyConfirm()");
		
		String nextPage = "board/reply_ok";
		
		boardDto.setM_id(principal.getName());
		
		int result = boardService.replyConfirm(boardDto);
		
		if (result <= 0)
			nextPage ="board/reply_ng";
		
		return nextPage;
	}
	
	@GetMapping("/modify_form")
	public String modifyForm(@RequestParam("b_no") int b_no, Model model) {
		log.info("modifyForm()");
		
		String nextPage = "board/modify_form";
		
		BoardDto boardDto = boardService.modifyForm(b_no);
		
		model.addAttribute("boardDto", boardDto);
		
		return nextPage;
	}
	
	@PostMapping("/modify_confirm")
	public String modifyConfirm(BoardDto boardDto) {
		log.info("modifyConfirm()");
		
		String nextPage = "board/modify_ok";
		
		int result = boardService.modifyConfirm(boardDto);
		if(result <= 0 )
			nextPage = "board/modify_ng";
		return nextPage;
	}
	

}
