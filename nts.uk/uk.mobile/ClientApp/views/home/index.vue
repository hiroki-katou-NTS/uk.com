<template>
  <div id="home">
    <button class="btn btn-info" v-click="showPicker">{{'show picker' | i18n}}</button>
    <pre>{{selecteds}}</pre>
    <pre>{{dataSources}}</pre>
  </div>
</template>