<script lang="ts">
    import logo from "../assets/logo.png";
    import {fetchUserInformation, login} from "../api/user";

    function parseLoginToken() {
        let hash = window.location.hash.substring(1);
        if (hash.startsWith("access_token")) {
            let token = hash.split("=")[1];

            fetchUserInformation(token).then(resp => {
                login(resp);
            });
        }
    }

    parseLoginToken();
</script>

<div class="center">
    <div class="login-container">
        <img class="logo" src={logo} alt="Logo" />
        <div class="card">
            <h1>Login</h1>
            <a class="login-btn" href="http://localhost:8080/authorize?client_id=dc783715-5e6e-4146-a7bd-7384af6928c4&state=u89eruv234b9783745897b&redirect_uri=http://localhost:8081/login">Authenticate with GateKeeper</a>
        </div>
    </div>

</div>

<style lang="scss">

  .login-btn {
    padding: 2rem;
    background-color: #724efc;

    border-radius: 10px;

    color: white;
    font-size: 1.5rem;
    transition: .3s;
    cursor: pointer;

    &:hover {
      transform: translateY(-4px);
    }
  }

    .logo {
      width: auto;
      max-width: 20rem;
    }

    .card {
      display: flex;
      flex-direction: column;

      gap: 1rem;
      text-align: center;
      max-width: 30rem;
    }

    .center {
      display: grid;
      place-items: center;
    }
</style>