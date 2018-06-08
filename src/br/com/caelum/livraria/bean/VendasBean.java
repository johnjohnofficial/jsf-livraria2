package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class VendasBean implements Serializable {
	
	@Inject
	private LivroDao livroDao;
	
	private List<Venda> getVendas(long seed) {
		
		List<Livro> livros = this.livroDao.listaTodos();
		List<Venda> vendas = new ArrayList<Venda>();

		Random random = new Random(seed);
		
		for (Livro livro : livros) {
			Integer quantidade = random.nextInt(500);
			vendas.add(new Venda(livro, quantidade));
		}		
		
		return vendas;
	}
	
	public BarChartModel getVendasModel() {
		
		BarChartModel model = new BarChartModel();
		model.setTitle("Vendas"); // setando o t�tulo do gr�fico
		model.setLegendPosition("ne"); // nordeste
		model.setAnimate(true); // Gr�fico animado
		
		// pegando o eixo X do gr�fico e setando o t�tulo do mesmo
		Axis xAxis = model.getAxis(AxisType.X);
		xAxis.setLabel("T�tulo");
		
		// pegando o eixo Y do gr�fico e setando o t�tulo do mesmo
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");
		
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016"); 
		
		
		List<Venda> vendas = this.getVendas(1234);
		
		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		model.addSeries(vendaSerie);
		
		ChartSeries vendaSerie2015 = new ChartSeries();
		vendaSerie2015.setLabel("Vendas 2015");
		
		vendas = this.getVendas(4321);
		
		for (Venda venda : vendas) {
			vendaSerie2015.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		model.addSeries(vendaSerie2015);
		
		return model;
	}

}
