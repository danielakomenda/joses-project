import Home from "./pages/Home.svelte";
import Schiedsrichters from "./pages/Schiedsrichters.svelte";
import SchiedsrichtersDetails from "./pages/SchiedsrichtersDetails.svelte";
import Trainings from "./pages/Trainings.svelte";
import TrainingsDetails from "./pages/TrainingsDetails.svelte";

export default {
  // Home
  '/': Home,
  '/home': Home,

  // Schiedsrichter
  '/schiedsrichters': Schiedsrichters,
  '/schiedsrichterdetails/:id': SchiedsrichtersDetails, // Hier die Korrektur

  // Training
  '/trainings': Trainings,
  '/trainingsdetails/:id': TrainingsDetails
};
