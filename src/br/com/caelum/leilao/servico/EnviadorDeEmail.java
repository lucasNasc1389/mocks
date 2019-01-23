/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Leilao;

/**
 *
 * @author User
 */
public interface EnviadorDeEmail {
    void envia(Leilao leilao);
}
