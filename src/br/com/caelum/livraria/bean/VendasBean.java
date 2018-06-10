package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.VendaDao;
import br.com.caelum.livraria.modelo.Venda;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class VendasBean implements Serializable {
	
	@Inject
	private VendaDao vendaDao;
	
	private List<Venda> getVendas() {
		return this.vendaDao.vendas();
	}
	
	public BarChartModel getVendasModel() {
		
		BarChartModel model = new BarChartModel();
		model.setTitle("Vendas"); // setando o t�tulo do gr�fico
		model.setLegendPosition("ne"); // nordeste
		model.setAnimate(true); // Gr�fico animado
		
		// pegando o eixo X do gr�fico e setando o t�tulo do mesmo
		Axis xAxis = model.getAxis(AxisType.X);
		xAxis.setLabel("Titulo");
		
		// pegando o eixo Y do gr�fico e setando o t�tulo do mesmo
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");
		
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016"); 
		
		
		List<Venda> vendas = this.getVendas();
		
		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		model.addSeries(vendaSerie);
		
		return model;
	}

}
