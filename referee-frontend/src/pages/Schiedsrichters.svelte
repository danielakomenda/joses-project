<script>
    import axios from "axios";

    const api_root = window.location.origin;

    let schiedsrichters = [];
    let schiedsrichter = {
        id: null,
        email: null,
        name: null,
        level: null,
    };

    function getSchiedsrichters() {
        var config = {
            method: "get",
            url: api_root + "/api/schiedsrichter",
            headers: {},
        };

        axios(config)
            .then(function (response) {
                schiedsrichters = response.data;
            })
            .catch(function (error) {
                alert("Noch keine Schiedsrichter aufgelistet");
                console.log(error);
            });
    }
    getSchiedsrichters();

    function createSchiedsrichter() {
        var config = {
            method: "post",
            url: api_root + "/api/schiedsrichter",
            headers: {
                "Content-Type": "application/json",
            },
            data: schiedsrichter,
        };

        axios(config)
            .then(function (response) {
                alert("Schiedsrichter created");
                getSchiedsrichters();
            })
            .catch(function (error) {
                alert("Could not create Schiedsrichter");
                console.log(error);
            });
    }
</script>

<h1 class="mt-3">Schiedsrichter</h1>
<form class="mb-5">
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="description">Name</label>
            <input
                bind:value={schiedsrichter.name}
                class="form-control"
                id="name"
                type="text"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="email">E-Mail</label>
            <input
                bind:value={schiedsrichter.email}
                class="form-control"
                id="earnings"
                type="email"
            />
        </div>
        <div class="row mb-3">
            <div class="col">
                <label class="form-label" for="level">Qualifikation</label>
                <input
                    bind:value={schiedsrichter.level}
                    class="form-control"
                    id="level"
                    type="number"
                    min="1"
                    max="6"
                />
            </div>
        </div>
        <button
            type="button"
            class="btn btn-primary"
            on:click={createSchiedsrichter}>Erstellen</button
        >
    </div>
</form>

<h1>Alle Schiedsrichter</h1>
<table class="table">
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">E-Mail</th>
            <th scope="col">Qualifikation</th>
        </tr>
    </thead>
    <tbody>
        {#each schiedsrichters as schiedsrichter}
            <tr>
                <td><a href={`#/schiedsrichterdetails/${schiedsrichter.id}`}>{schiedsrichter.email}</a></td>
                <td>{schiedsrichter.name}</td>
                <td>{schiedsrichter.level}</td>
            </tr>
        {/each}
    </tbody>
</table>
