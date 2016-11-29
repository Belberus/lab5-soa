package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        int max = q.indexOf("max");         // We check at wich position the word "max" is in q
        String s = q.substring(max);        // We take the substring at that position
        String[] split = s.split(":");      // We divide that substring in two, [0] is the word max | [1] is the max value
        s = split[1];
        Integer count = Integer.parseInt(s);
        q = q.substring(0,max);

        // Now we create the headers
        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put("CamelTwitterKeywords",q);
        headers.put("CamelTwitterCount",count);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);


    }
}