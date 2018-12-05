package semantic.model;

public class DoItYourselfModel implements IModelFunctions
{
	IConvenienceInterface model;
	
	public DoItYourselfModel(IConvenienceInterface m) {
		this.model = m;
	}

	@Override
	public String createPlace(String name) {
		// System.out.println(model.getEntityURI("Lieu").get(0));
		return model.createInstance(name,model.getEntityURI("Lieu").get(0));
	}

	@Override
	public String createInstant(TimestampEntity instant) {
		for(int i=0;i<model.listLabels(model.getEntityURI("Instant").get(0)).size();i++) {
			if(model.listLabels(model.getEntityURI("Instant").get(0)).get(i)==instant.timestamp) {
				return null;
			}
		}
		
		String URI = model.createInstance(instant.timestamp, model.getEntityURI("Instant").get(0));
		model.addDataPropertyToIndividual(URI, model.getEntityURI("a pour timestamp").get(0),instant.timestamp);
		return URI;
	}

	@Override
	public String getInstantURI(TimestampEntity instant) {
		String URI = null;
		String Instant_URI = model.getEntityURI("Instant").get(0);		
		
		for(int i=0;i<model.getInstancesURI(Instant_URI).size();i++) {
			if(model.hasDataPropertyValue(model.getInstancesURI(model.getEntityURI("Instant").get(0)).get(i), model.getEntityURI("a pour timestamp").get(0), instant.timestamp)) {
				URI = model.getInstancesURI(model.getEntityURI("Instant").get(0)).get(i);
			}
		}
		
		return URI;
	}

	@Override
	public String getInstantTimestamp(String instantURI)
	{
		String timestamp = null;
		
//		for(int i=0; i<model.getInstancesURI(model.getEntityURI("Instant").get(0)).size(); i++) {
//			if(model.getInstancesURI(model.getEntityURI("Instant").get(0)).get(i).equals(instantURI)) {
//				// timestamp = model.listLabels(model.getInstancesURI(model.getEntityURI("Instant").get(0)).get(i)).get(0);
//				
//			}
//		}
		
		for(int n=0;n<model.listProperties(instantURI).size();n++) {
			if(model.listProperties(instantURI).get(n).get(0).equals(model.getEntityURI("a pour timestamp").get(0))) {
				timestamp = model.listProperties(instantURI).get(n).get(1);
			}
		}
		
		
		return timestamp;
	}

	@Override
	public String createObs(String value, String paramURI, String instantURI) {
		String obsURI = null;
		
		obsURI = model.createInstance(value, model.getEntityURI("Observation").get(0));
		model.addObjectPropertyToIndividual(obsURI,model.getEntityURI("a pour date").get(0),instantURI);
		model.addObservationToSensor(obsURI, model.whichSensorDidIt(getInstantTimestamp(instantURI), paramURI));
		model.addObjectPropertyToIndividual(obsURI,model.getEntityURI("mesure").get(0),paramURI);
		model.addDataPropertyToIndividual(obsURI,model.getEntityURI("a pour valeur").get(0),value);
		
		return obsURI;
	}
}
