/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro =  mock(EnviadorDeEmail.class);
        //ensinando o mock a reagir da maneira que esperamos
        when(daoFalso.correntes()).thenReturn(leiloesAntigos);
        
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        
        encerrador.encerra();
        
        Assert.assertTrue(leilao1.isEncerrado());
        Assert.assertTrue(leilao2.isEncerrado());
        Assert.assertEquals(2, encerrador.getTotalEncerrados(), 0.00001);
        
    }
    
    @Test
    public void NaoDeveEncerrarLeiloesCriadosOntem() {
        
        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);
        
        Leilao leilao1 = new CriadorDeLeilao().para("Super Famicom").naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Nintendo 64").naData(ontem).constroi();
        
        List<Leilao>leiloesAntigos = Arrays.asList(leilao1,leilao2);
        
        //Criando Mock
        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        
        
        //ensinando o mock a reagir da maneira que esperamos
        when(daoFalso.correntes()).thenReturn(leiloesAntigos);
        
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();
        
        Assert.assertEquals(0, encerrador.getTotalEncerrados(), 0.00001);
        Assert.assertFalse(leilao1.isEncerrado());
        Assert.assertFalse(leilao2.isEncerrado());
       
        }
    
    @Test
    public void NaoDeveRetornarNada() {
        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        
        when(daoFalso.correntes()).thenReturn(new ArrayList<Leilao>());

       EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);
       EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();

        Assert.assertEquals(0, encerrador.getTotalEncerrados());
    }
    
    @Test
    public void deveAtualizarLeiloesEncerrados() {
        Calendar antigas = Calendar.getInstance();
        antigas.set(2000, 12, 2);
        
        
        Leilao leilao1 = new CriadorDeLeilao().para("Super Famicom").naData(antigas).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Nintendo 64").naData(antigas).constroi();
        
        List<Leilao>leiloesAntigos = Arrays.asList(leilao1,leilao2);
        
        //Criando Mock
        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        
        //ensinando o mock a reagir da maneira que esperamos
        when(daoFalso.correntes()).thenReturn(leiloesAntigos);
        
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();
        
        verify(daoFalso, times(1)).atualiza(leilao1);
    }
    
    @Test
    public void naoDeveEncerrarLeiloesQueComecaramMenosDeUmaSemanaAtras() {

        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
            .naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
            .naData(ontem).constroi();

        RepositorioDeLeiloes daoFalso = mock(LeilaoDao.class);
       
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();

        Assert.assertEquals(0, encerrador.getTotalEncerrados());
        Assert.assertFalse(leilao1.isEncerrado());
        Assert.assertFalse(leilao2.isEncerrado());

        verify(daoFalso, never()).atualiza(leilao1);
        verify(daoFalso, never()).atualiza(leilao2);
    }
}
