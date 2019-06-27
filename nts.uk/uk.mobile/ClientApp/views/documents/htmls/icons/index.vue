<template>
  <div class="documentshtmlsicons">
    <markdown />
    <div class="row justify-content-md-center">
      <div class="col-md-3 mb-5">
        <nts-search-box v-model="keyword"/>
      </div>
    </div>
    <div class="row">
      <div class="col-6 col-sm-4 col-md-3 col-lg-1 text-center uk-icons" v-for="(item, k) in filtereds" v-bind:key="k">
        <i v-bind:class="item.class"></i>
        <p class="uk-icons-name m-0 p-0">{{item.code}}</p>
        <p class="uk-icons-name">{{item.class}}</p>
      </div>
    </div>
  </div>
</template>