
<script>
	import Router from "svelte-spa-router";
	import routes from "./routes";
	import "./app.css";
</script>

<div id="app">
	<nav class="navbar navbar-expand-lg bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#/">Referee</a>
			<img class="logo" src="assets/sfv.png" alt="SFV Logo">
			<button
				class="navbar-toggler"
				type="button"
				data-bs-toggle="collapse"
				data-bs-target="#navbarNav"
				aria-controls="navbarNav"
				aria-expanded="false"
				aria-label="Toggle navigation"
			>
				<span class="navbar-toggler-icon" />
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item">
						<a
							class="nav-link"
							aria-current="page"
							href="#/schiedsrichters">Schiedsrichter</a
						>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#/trainings">Training</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<Router {routes} />
	</div>
</div>

<style>
	.logo {
		width: 40px; /* Stellen Sie die Breite entsprechend Ihren Anforderungen ein */
		height: auto; /* Das Verhältnis beibehalten */
		margin-top: 5px; /* Platzierung anpassen */
	}
</style>