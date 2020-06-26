const mongoose=require('mongoose');
const express=require('express');
const devDebug=require('debug')('devMode');
const dbDebug=require('debug')('db');
// const locationRouter=require('./routes/location');
 
const app=express();
// const router=express.Router();

app.use(express.json());
app.use(express.urlencoded({extended:true}));
// app.use('/',locationRouter);


mongoose.connect("mongodb://localhost/LocationTracker",{
    useNewUrlParser: true,
    useUnifiedTopology: true 
})
        .then(()=>devDebug("MongoDb Connected.."))
        .catch(err=>devDebug("Error: ",err.message));


const vehicleSchema=mongoose.Schema({
            owner:String,
            contact:String,
            vehicleRegNo:String,
            vehicleType:String,
            locations:[{time:{type:Date,default:Date.now},latitude:String,longitude:String}],
            currentLocation:{time:{type:Date,default:Date.now},latitude:String,longitude:String}
        });        
        
const Vehicle=mongoose.model('Vehicle',vehicleSchema);
        
module.exports=Vehicle;        


async function insertVehicle(vehicle)
{
    try{
        const v=await new Vehicle(vehicle);
        v.save();
        dbDebug(v);
    }
    catch(err)
    {
        dbDebug(err.message);
    }
    

}



const vehicleObj={
                  owner:"Abhay",
                  contact:"9892830144",
                  vehicleRegNo:"MH038345",
                  vehicleType:"Bus",
                  locations:[{latitude:"4",longitude:"5"}],
                  currentLocation:{latitude:"4",longitude:"5"}
                }

// insertVehicle(vehicleObj);




// default path: /

async function getAllVehicles()
{
    const vehicles= await Vehicle.find().select("owner vehicleType");
    // devDebug(vehicles);
    // console.log(vehicles);
    return vehicles;

}

async function getVehicleByID(id)
{
    return await Vehicle.findById(id);
}

async function updateVehicleLocation(id,obj)
{
   
    const vehicle=await Vehicle.findById(id);

    if(!vehicle)
         return new Error("Not Found!");

    vehicle.locations.push(obj);
    
    vehicle.currentLocation=obj;

    return await vehicle.save()
}

app.get('/',(req,res)=>{

    getAllVehicles()
    .then(obj=>res.send(obj))
    .catch(err=>{dbDebug(err); res.status(400).send("Couldn't fetch from MongoDB..");})
    ;
});

//on registering every vehicle will get their id which also happens to be _id in mongodb
app.put('/api/newLocation',(req,res)=>{
     
    const vehicleId=req.body.id;
    const latitude=req.body.latitude;
    const longitude=req.body.longitude;

    if(!latitude || !longitude || !vehicleId)
    {
          res.status(404).send("No Coordinates recieved or vehicle not registered!");
          return;
    } 

    const updatingObject={ time:Date.now(),latitude,longitude }

    updateVehicleLocation(vehicleId, updatingObject)
        .then((vehicle)=>res.status(200).send(vehicle))
        .catch(err=>res.status(400).send(err.message));

});

const port=process.env.PORT || 3000;
app.listen(port,()=>dbDebug(`Listening to port ${port}`));
