/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import java.util.Calendar;
import org.junit.Test;

/**
 *
 * @author lucas
 */
public class EncerradorDeLeilaoTeste {
    
    @Test
    public void DeveEncerrarLeiloesDaUltimaSemana() {
        
        Calendar antigas = Calendar.getInstance();
        antigas.set(2000, 12, 2);
        
        Leilao leilao1 = new CriadorDeLeilao().para("Super Famicom").naData(antigas).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Nintendo 64").naData(antigas).constroi();
        
        <List>Leilao leiloesAntigos = 
        
    }
}
