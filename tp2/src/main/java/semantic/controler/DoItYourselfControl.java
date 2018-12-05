package semantic.controler;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import semantic.model.IConvenienceInterface;
import semantic.model.IModelFunctions;
import semantic.model.ObservationEntity;

public class DoItYourselfControl implements IControlFunctions
{
	private IConvenienceInterface model;
	private IModelFunctions customModel;
	
	public DoItYourselfControl(IConvenienceInterface model, IModelFunctions customModel)
	{
		this.model = model;
		this.customModel = customModel;
	}
	
	@Override
	/**
	 * This function parses the list of observations extracted from the dataset, 
	 * and instanciates them in the knowledge base. 
	 * @param obsList
	 * @param phenomenonURI
	 */
	public void instantiateObservations(List<ObservationEntity> obsList, String paramURI) {
		int i=0;
		Iterator<ObservationEntity> obs = obsList.iterator();
		while(obs.hasNext()) {
			ObservationEntity current = obs.next();
			String instantURI = customModel.createInstant(current.getTimestamp());
			customModel.createObs(current.getValue().toString(), paramURI, instantURI);
			//System.out.println(current.getTimestamp());
		}
		System.out.println("Instanciated observations: " +i);
	}
}
