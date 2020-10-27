<template>
  <div class="kafs20a2">
    <KafS00AComponent class="mx-n3"
    v-if="!!kafS00AParams" 
    v-bind:params="kafS00AParams" 
    />
    <span>This is KAFS20</span>
    <KafS00BComponent
    v-if="!!kafS00BParams"
    v-bind:params="kafS00BParams"
    />
    <span>This is KAFS20</span>
    <KafS00CComponent 
    v-if="!!kafS00CParams"
    v-bind:params="kafS00CParams"
    />
  </div>
</template>