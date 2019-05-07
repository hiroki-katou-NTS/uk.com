<template>
  <div id="home">
    <button class="btn btn-info" v-on:click="show = true">{{'show picker' | i18n}}</button>
    <pre>{{tets}}</pre>
    <pre>{{selecteds.year}}</pre>
    <mpkr
      v-model="selecteds"
      v-bind:show="show"
      v-bind:dataSources="dataSources"
      v-on:close="show = false"
      v-on:select="() => {}"
      v-on:input="() => {}"
    />

    <div class="infinity-picker android centered">
      <div class="ipkr-caption">
        <div class="ipkr-caption-item">{{'04:05'}}</div>
      </div>
      <div class="ipkr-content">
        <ipkr
          option-text="text"
          option-value="value"
          v-model="selecteds.year"
          v-bind:data-sources="dataSources.year"
          v-on:test="viewPortClicked"
        />
        <ipkr
          option-text="text"
          option-value="value"
          v-model="selecteds.month"
          v-bind:data-sources="dataSources.month"
          v-on:test="viewPortClicked"
        />
        <ipkr
          option-text="text"
          option-value="value"
          v-model="selecteds.day"
          v-bind:data-sources="dataSources.day"
          v-on:test="viewPortClicked"
        />
        <ipkr
          option-text="text"
          option-value="value"
          v-model="selecteds.day"
          v-bind:data-sources="dataSources.day"
          v-on:test="viewPortClicked"
        />
        <ipkr
          option-text="text"
          option-value="value"
          v-model="selecteds.day"
          v-bind:data-sources="dataSources.day"
          v-on:test="viewPortClicked"
        />
      </div>
      <div class="ipkr-navbar">
        <a class="ipkr-navbar-btn">Cancel</a>
        <a class="ipkr-navbar-btn">Accept</a>
      </div>
    </div>
  </div>
</template>