<script>
    import axios from "axios";
    export let params = {};
    const api_root = window.location.origin;
    
    let schiedsrichter_id;


    let schiedsrichter = {
      email: null,
      name: null,
      level: null,
      trainings: null,
      numberOfTrainings: null
    };
  
  
    $: {
      schiedsrichter_id = params.id;
      getSchiedsrichter();
    }
  

  function getSchiedsrichter() {
    console.log(schiedsrichter_id)
    var config = {
      method: "get",
      url: api_root + "/api/schiedsrichter/id/" + schiedsrichter_id,
      headers: {},
    };
    axios(config)
      .then(function (response) {
        schiedsrichter = response.data;
      })
      .catch(function (error) {
        alert("Noch keine Trainings erfasst");
        console.log(error);
      });
  }
  
  </script>
  
  <h1>Schiedsrichterdetails</h1>
  <p>Name: {schiedsrichter.name}</p>
  <p>E-Mail: {schiedsrichter.email}</p>
  <p>Qualifikation: {schiedsrichter.level}</p>
  <p>Anzahl der Trainings: {schiedsrichter.numberOfTrainings}</p>
  