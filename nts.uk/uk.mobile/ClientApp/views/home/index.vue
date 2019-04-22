<template>
  <div id="home">
    <button class="btn btn-info" v-click="showPicker">{{'show picker' | i18n}}</button>
    <pre v-ripple="'rgba(255, 0, 0, 0.35)'">{{validations}}</pre>
  </div>
</template>