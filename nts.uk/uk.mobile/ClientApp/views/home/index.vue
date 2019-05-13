<template>
  <div id="home">
    <button class="btn btn-info" v-on:click="showPicker">{{'show picker' | i18n}}</button>
    <pre>{{selecteds}}</pre>
  </div>
</template>