<template>
  <div class="documentshtmlscolor">
    <div class="row mb-3">
      <div class="col-md-4 mb-3">
        <h4>UK background color pallet</h4>
        <ul class="list-group border-0">
          <li class="list-group-item border-0 uk-color-swatch" v-for="(l, j) in backgroundColors" v-bind:key="j" v-bind:class="'uk-bg-' + l">{{'uk-bg-' + l}}</li>
        </ul>
      </div>
      <div class="col-md-4 mb-3">
        <h4>UK Mobile background color pallet</h4>
        <ul class="list-group border-0">
          <li class="list-group-item border-0 uk-color-swatch" v-for="(l, j) in ukMobiles" v-bind:key="j" v-bind:class="'uk-bg-' + l">{{'uk-bg-' + l}}</li>
        </ul>
      </div>
      <div class="col-md-4 mb-3">
        <h4>UK Mobile forceground color pallet</h4>
        <ul class="list-group border-0">
          <li class="list-group-item border-0 uk-color-swatch" v-for="(l, j) in textColors" v-bind:key="j" v-bind:class="'uk-text-' + l">{{'uk-text-' + l}}</li>
        </ul>
      </div>
    </div>
  </div>
</template>