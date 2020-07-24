<template>
  <div>
    <div id="clock">
      <label id="date">
        {{ current.date }}
      </label>
      <button class="btn btn-primary btn-block" v-click:500="login">
        {{ "KDPS01_25" | i18n }}
      </button>
      <label id="time">
        {{ current.time }}
      </label>
    </div>
    <div>
    <pre>
    {{ comment }}
    </pre>

    </div>
    <div>
 <button v-for="(button) in setting.butons" v-bind:key="button.type" >{{ button.displayText | i18n }}</button>
    </div>
  </div>
</template>
