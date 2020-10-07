<template>
  <div class="kafs04a0">
    <kaf-s04-a
      v-if="showComponentA"
      v-bind:mode="paramsAComponent.mode"
      @nextComponentA0="handlNextComponentA0"
    />
    <kaf-s04-a1
      v-if="showComponentA1"
      v-bind:mode="paramsAComponent.mode"
      @showComponentA="handleShowComponentA"
      v-bind:paramsAComponent="paramsAComponent"
    />
  </div>
</template>