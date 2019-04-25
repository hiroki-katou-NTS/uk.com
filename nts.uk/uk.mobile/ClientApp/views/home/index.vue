<template>
  <div id="home">
    <button class="btn btn-info" v-on:click="show = true">{{'show picker' | i18n}}</button>
    <mpkr
      v-model="selecteds"
      v-bind:show="show"
      v-bind:dataSources="dataSources"
      v-on:close="show = false"
      v-on:select="() => {}"
      v-on:input="() => {}"
    />

    <div class="datepicker ios">
      <div class="datepicker-navbar">
        <a class="datepicker-navbar-btn">完成</a>
        <a class="datepicker-navbar-btn">取消</a>
      </div>
      <div class="datepicker-content">
        <ipkr v-model="selecteds.year" v-bind:data-sources="[2010,2011,2012,2013,2014]" v-on:input="viewPortClicked"/>
      </div>
    </div>
  </div>
</template>