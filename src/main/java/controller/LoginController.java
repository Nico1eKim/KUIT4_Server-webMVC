package controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        User user = MemoryUserRepository.getInstance().findUserById(userId);

        // 로그인 실패 시 sendRedirect를 사용하여 실패 페이지로 이동시킴
        // 새로운 HTTP 요청이 발생하기 때문에 요청/응답 사이클이 새로 시작됨
        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("user 로그인 실패");
            return "redirect:/user/login_failed.jsp";
        }

        // 로그인 성공 시 forward를 사용하여 로그인 성공 후 데이터를 유지하며 페이지를 렌더링 시킴
        // 기존 요청과 응답을 그대로 전달하기 때문에 같은 요청 객체와 데이터를 사용할 수 있음
        System.out.println("user 로그인 성공");
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return "/";
    }
}
