<script>
// @ts-nocheck

  import axios from "axios";
  import { querystring } from "svelte-spa-router";

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


  let currentPage;
  let nrOfPages = 0;
  let defaultPageSize = 4;

  let minLevel;
  let trainingsType;
  let loading = false;


  $: {
    //if ($jwt_token !== "") {
      let searchParams = new URLSearchParams($querystring);

      if (searchParams.has("page")) {
        currentPage = searchParams.get("page");
      } else {
        currentPage = "1";
      }
      getTrainings();
    }
    //}
  


  function getTrainings() {
    let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
    if (minLevel) {
      query += "&min=" + (minLevel - 1);
    }
    if (trainingsType && trainingsType !== "ALL") {
      query += "&type=" + trainingsType;
    }
    var config = {
      method: "get",
      url: api_root + "/api/training" + query,
      //headers: { Authorization: "Bearer " + $jwt_token }, // Token wird als Header übergeben
    };
    axios(config)
      .then(function (response) {
        trainings = response.data.content;
        nrOfPages = response.data.totalPages;
      })
      .catch(function (error) {
        alert("Noch keine Trainings erfasst");
        console.log(error);
      });
  }



  function createTraining() {
    loading = true;
    var config = {
      method: "post",
      url: api_root + "/api/training",
      headers: {
        "Content-Type": "application/json",
      },
      data: training,
    };
    console.log(config);
    axios(config)
      .then(function (response) {
        alert("Training created");
        loading = false;
        getTrainings();
      })
      .catch(function (error) {
        alert("Could not create Training");
        console.log(error);
      });
  }

  function assignToMe(jobId) {
    var config = {
      method: "put",
      url: api_root + "/api/service/assigntraining?jobId=" + jobId,
      //headers: { Authorization: "Bearer " + $jwt_token },
    };
    axios(config)
      .then(function (response) {
        alert("Job assigned");
        getTrainings();
      })
      .catch(function (error) {
        alert("Could not assign job to me");
        console.log(error);
      });
  }

  function completeTraining(jobId) {
    var config = {
      method: "put",
      url: api_root + "/api/service/completejob?jobId=" + jobId,
      //headers: { Authorization: "Bearer " + $jwt_token },
    };
    axios(config)
      .then(function (response) {
        getTrainings();
      })
      .catch(function (error) {
        alert("Could not complete the job");
        console.log(error);
      });
  }
</script>


<!--{#if $user.user_roles && $user.user_roles.includes("admin")}-->
<h1>Training neu anlegen</h1>
<form class="mb-5">
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="description">Description</label>
      <input bind:value={training.description} class="form-control" id="description" type="text" />
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
      <input bind:value={training.date} class="form-control" id="date" type="date" />
    </div>

    <div class="col">
      <label class="form-label" for="minLevel">Qualifikation</label>
      <input bind:value={training.minLevel} class="form-control" id="minLevel" type="number" min="1" max="6" />
    </div>

    <div class="col">
      <label class="form-label" for="location">Sportanlage</label>
      <input bind:value={training.location} class="form-control" id="location" type="text" />
    </div>
  </div>

  <button type="button" class="btn btn-primary" on:click={createTraining}>Veröffentlichen</button>
</form>
<!--{/if}-->

<h1>Alle Trainings</h1>


<div class="row my-3">
    <!--FILTER FOR EARNINGS-->
    <div class="col-auto">
      <label for="" class="col-form-label">Mindest-Level: </label>
    </div>
    <div class="col3">
      <input class="form-contol" type="number" id=minLevel placeholder="min" bind:value={minLevel} />
    </div>
  
    <!--FILTER FOR JOBTYPE-->
    <div class="col-auto">
      <label for="" class="col-form-label">Trainings-Type: </label>
    </div>
    <div class="col-3">
      <select bind:value={trainingsType} class="form-select" id="typefilter" type="text">
        <option value="ALL" />
        <option value="KONDITION">KONDITION</option>
        <option value="KRAFT">KRAFT</option>
        <option value="FUN">FUN</option>
        <option value="OTHER">OTHER</option>
      </select>
    </div>
  
    <!--APPLY-BUTTON-->
    <div class="col-3">
      <a class="btn btn-primary" href={"#/training?page=1&trainingsType=" + trainingsType + "&minLevel=" + minLevel} role="button"
        >Apply</a
      >
    </div>
  </div>


<table class="table">
  <thead>
    <tr>
      <th scope="col">Description</th>
      <th scope="col">Type</th>
      <th scope="col">Datum</th>
      <th scope="col">Qualifikation</th>
      <th scope="col">Sportanlage</th>
      <th scope="col">State</th>
      <th scope="col">Anmelden</th>
      <!--  {#if $user.user_roles && $user.user_roles.includes("admin")}-->
      <th scope="col">Beenden</th>
      <!--{/if}-->
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
        <td
          ><button type="button" class="btn btn-primary btn-sm" on:click={() => assignToMe(training.id)}>
            Anmelden
          </button></td
        >

        <!--{#if $user.user_roles && $user.user_roles.includes("admin")}-->
        <td
          ><button type="button" class="btn btn-primary btn-sm" on:click={() => completeTraining(training.id)}>
            Beenden
          </button></td
        >
        <!--{/if}-->
      </tr>
    {/each}
  </tbody>
</table>

<nav>
    <ul class="pagination">
      {#each Array(nrOfPages) as _, i}
        <li class="page-item">
          <a class="page-link" class:active={currentPage == i + 1} href={"#/trainings?page=" + (i + 1)}>{i + 1}</a>
        </li>
      {/each}
    </ul>
  </nav>
