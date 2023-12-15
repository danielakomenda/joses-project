
<script>
  import axios from "axios";

  const api_root = window.location.origin;

  let trainings = [];
  
  let training = {
    description: null,
    date: null,
    trainingType: null,
    location: null, 
    minLevel: null,
    verband: null,
  };

  function getTrainings() {
    var config = {
      method: "get",
      url: api_root + "/api/training",
      headers: {},
    };

    axios(config)
      .then(function (response) {
        trainings = response.data;
      })
      .catch(function (error) {
        alert("Noch keine Trainings erfasst");
        console.log(error);
      });
  }
  getTrainings();

  function createTraining() {
    var config = {
      method: "post",
      url: api_root + "/api/training",
      headers: {
        "Content-Type": "application/json",
      },
      data: training,
    };
    console.log('heho');
    console.log(config);

    axios(config)
      .then(function (response) {
        alert("Training created");
        getTrainings();
      })
      .catch(function (error) {
        alert("Could not create Training");
        console.log(error);
      });
  }
</script>

<h1 class="mt-3">Training</h1>
<form class="mb-5">
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="description">Description</label>
      <input
        bind:value={training.description}
        class="form-control"
        id="description"
        type="text"
      />
    </div>
  </div>
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="type">Type</label>
      <select bind:value={training.trainingType} class="form-select" id="type">
        <option value="OTHER">OTHER</option>
        <option value="KONDITION">KONDITION</option>
        <option value="KRAFT">KRAFT</option>
        <option value="FUN">FUN</option>
      </select>
    </div>

    <div class="col">
      <label class="form-label" for="date">Datum</label>
      <input
        bind:value={training.date}
        class="form-control"
        id="date"
        type="date"
      />
    </div>
    
    <div class="col">
      <label class="form-label" for="minLevel">Qualifikation</label>
      <input
        bind:value={training.minLevel}
        class="form-control"
        id="minLevel"
        type="number"
        min="1"
        max="6"
        />
    </div>

    <div class="col">
      <label class="form-label" for="location">Sportanlage</label>
      <input
        bind:value={training.location}
        class="form-control"
        id="location"
        type="text"
      />
    </div>
  </div>


  <button type="button" class="btn btn-primary" on:click={createTraining}
    >Veröffentlichen</button
  >
</form>


<h1>Alle Trainings</h1>
<table class="table">
  <thead>
    <tr>
      <th scope="col">Description</th>
      <th scope="col">Type</th>
      <th scope="col">Datum</th>
      <th scope="col">Qualifikation</th>
      <th scope="col">Sportanlage</th>
      <th scope="col">State</th>
    </tr>
  </thead>
  <tbody>
    {#each trainings as training}
      <tr>
        <td> <a href={`#/trainingsdetails/${training.id}`}>{training.description}</a></td>
        <td>{training.trainingType}</td>
        <td>{training.date}</td>
        <td>{training.minLevel}</td>
        <td>{training.location}</td>
        <td>{training.trainingState}</td>
      </tr>
    {/each}
  </tbody>
</table>
