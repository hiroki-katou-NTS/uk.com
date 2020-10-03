<template>
  <div class="kafs04a">
    <kaf-s00-a 
    v-if="kafS00AParams" 
    v-bind:params="kafS00AParams" />
    <kaf-s00-b 
    v-if="kafS00BParams" 
    v-bind:params="kafS00BParams" />
    <span>This only KAFS04 here</span>
    <kaf-s00-c 
    v-if="kafS00CParams" 
    v-bind:params="kafS00CParams" />
    <button
      @click="register()"
      type="button"
      class="btn btn-primary btn-block"
    >
      {{ "申請する" }}
    </button>
  </div>
</template>