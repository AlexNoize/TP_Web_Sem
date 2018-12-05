package semantic.controler;

import java.io.IOException;
import java.util.List;

import semantic.model.DoItYourselfModel;
import semantic.model.IModelFunctions;
import semantic.model.ObservationEntity;
import semantic.model.SemanticModel;
import semantic.view.JSONEndpoint;

public class Controler
{
	private SemanticModel model;
	private IModelFunctions customModel;
	private IControlFunctions customControl;
	
	public Controler()
	{
		// TODO : Change the path to the one to your own ontology
		this.model = new SemanticModel("tp-iss.ttl");
		this.customModel = new DoItYourselfModel(this.model);
		this.customControl = new DoItYourselfControl(this.model, this.customModel);
		this.initializeContext();
	}
	
	private void initializeContext()
	{
		this.customModel.createPlace("Aarhus");
	}
	
	public void exportModel(String path)
	{
		this.model.exportModel(path);
	}
	
	public SemanticModel getModel()
	{
		return this.model;
	}
	
	public IModelFunctions getCustomModel()
	{
		return this.customModel;
	}
	
	public IControlFunctions getCustomControl()
	{
		return this.customControl;
	}
	
	public static void main(String[] args) 
	{
		System.out.println("Main launch");
		Controler c = new Controler();
		try
		{
			// TODO : Change the path to the one of the file you downloaded
			System.out.println("Here 1");
			List<ObservationEntity> obsList = JSONEndpoint.parseObservations("./raw_weather_data_aarhus/tempm.txt");
			String tempURI = c.model.getEntityURI("Température").get(0);
			System.out.println("Parse 1 ok");
			c.getCustomControl().instantiateObservations(obsList, tempURI);
			System.out.println("Here 2");
			// TODO : Same thing, for humidity
//			obsList = JSONEndpoint.parseObservations("./raw_weather_data_aarhus/temp.txt");
//			String humidityURI = c.model.getEntityURI("Température").get(0);
//			c.getCustomControl().instantiateObservations(obsList, humidityURI);
//			System.out.println("Here 3");
			// TODO : Change the path so that it points where you want the export to be done
			c.exportModel("./export.ttl");
			System.out.println("Model exported!");
		}
		catch (IOException e)
		{
			System.out.println("Execution aborted");
			e.printStackTrace();
		}
	}
}
