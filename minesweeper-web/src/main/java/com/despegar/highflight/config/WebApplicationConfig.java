package com.despegar.highflight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.despegar.highflight.Minesweeper;
import com.despegar.highflight.MinesweeperImp;
import com.despegar.highflight.controller.MinesweeperController;

@Configuration
@EnableWebMvc
@ImportResource({
    "classpath:com/despegar/library/properties/base-properties-context.xml",
    "classpath:com/despegar/library/version/version-controller.xml",
    "classpath:com/despegar/library/healthcheck/health-check-controller.xml",
    "classpath:com/despegar/library/api/api-context.xml",
    "classpath:com/despegar/library/documentation/application-context.xml"})
public class WebApplicationConfig
    extends WebMvcConfigurerAdapter {

    @Bean
    public MinesweeperController minesweeperController() {
        MinesweeperController minesweeperController = new MinesweeperController();
        Minesweeper game = new MinesweeperImp();
        minesweeperController.setGame(game);
        return minesweeperController;
    }

}
