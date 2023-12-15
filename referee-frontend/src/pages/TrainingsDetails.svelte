<script>
    import axios from "axios";
    export let params = {};
    let trainings_id;
    const api_root = window.location.origin;
  
    $: {
      trainings_id = params.id;
      getTrainings();
    }
  
    let training = {
    description: null,
    location: null,
    trainingType: null,
    minLevel: null,
    minParticipants: null,
    date: null,
    participants: null
  };
  
  let numberOfParticipants = 0;

  function getTrainings() {
    console.log(trainings_id)
    var config = {
      method: "get",
      url: api_root + "/api/training/id/" + trainings_id,
      headers: {},
    };
    axios(config)
      .then(function (response) {
        training = response.data;
        training.date = training.date.split("T")[0];
        if (Array.isArray(training.participants)) {
        numberOfParticipants = training.participants.length;
      } else {
        numberOfParticipants = 0;
      }
      })
      .catch(function (error) {
        alert("Noch keine Trainings erfasst");
        console.log(error);
      });
  }

</script>
<h1>Trainingsdetails</h1>
<h2>{training.description}</h2>
<div class="infobox">
  <p><b>Sportanlage:</b></p>
  <p>{training.location}</p>
  <p><b>Datum:</b></p>
  <p>{training.date}</p>
  <p><b>Trainingsart:</b></p>
  <p>{training.trainingType}</p>
  <p><b>Mindest-Qualifikation:</b></p>
  <p>{training.minLevel}</p>
  <p><b>Anzahl Teilnehmer:</b></p>
  <p>{training.minParticipants}</p>
  <p><b>Anzahl Teilnehmer:</b></p>
  <p>{numberOfParticipants}</p>
</div>
