package co.yiiu.plugin;

import co.yiiu.annotation.Plugin;
import co.yiiu.domain.Model;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.undertow.server.HttpServerExchange;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

/**
 * Created by tomoya at 2020/1/9
 */
@Plugin
public class FreemarkerPlugin implements ViewResolvePlugin {

    private static final Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

    @Override
    public void render(HttpServerExchange exchange, String templatePath, Model model) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templatePath + ".ftl");
        StringWriter sw = new StringWriter();
        template.process(model.getAttribute(), sw);
        exchange.getResponseSender().send(sw.toString());
    }

    @Override
    public void init() throws IOException {
        FileTemplateLoader templateLoader = new FileTemplateLoader(new File(Objects.requireNonNull(ViewResolvePlugin.class.getClassLoader().getResource("templates")).getPath()));
        configuration.setTemplateLoader(templateLoader);
    }
}
