package net.iceyleagons.butler

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class WebController {

    @RequestMapping(value = ["/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/{y:[\\w\\-]+}"])
    fun index(): String = "index"

}