<template>
  <div id="home">
    <button class="btn btn-info" v-click="showPicker">{{'show picker' | i18n}}</button>
    <pre>{{validations}}</pre>
  </div>
</template>