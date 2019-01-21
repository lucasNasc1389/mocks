/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        
        List<Leilao>leiloesAntigos = Arrays.asList(leilao1,leilao2);
        
        //Criando Mock
        LeilaoDao daoFalso = mock(LeilaoDao.class);
        
        //ensinando o mock a reagir da maneira que esperamos
        when(daoFalso.correntes()).thenReturn(leiloesAntigos);
        
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();
        
        Assert.assertTrue(leilao1.isEncerrado());
        Assert.assertTrue(leilao2.isEncerrado());
        Assert.assertEquals(2, encerrador.getTotalEncerrados(), 0.00001);
        
    }
    
    @Test
    public void NaoDeveEncerrarLeiloesCriadosOntem() {
        
        int ontem = Calendar.
        
        System.out.println(ontem);
    }
}
