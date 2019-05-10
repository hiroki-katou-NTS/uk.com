<template>
  <div id="home">
    <button class="btn btn-info" v-on:click="show = true">{{'show picker' | i18n}}</button>
    <pre>{{tets}}</pre>
    <pre>{{selecteds.year}}</pre>
    <mpkr
      v-model="selecteds"
      v-bind:show="show"
      v-bind:title="title"
      v-bind:dataSources="dataSources"
      v-on:close="show = false"
      v-on:select="onSelect"
      v-on:input="() => {}"
    />
  </div>
</template>