<template>
  <div>
    <div id="clock" class="uk-bg-teal">
      <div class="col-12 pr-0 pt-2 font-weight-bold">
        <span id="date">
          {{ current.date }}
        </span>
        <button type="button" class="float-right btn btn-success">
          {{ "KDPS01_25" | i18n }}
        </button>
      </div>
       <div class="col-12 pt-2 px-5 font-weight-bold">
      <span id="time">{{ current.time }}</span>
       </div>
    </div>
    <div>
      <pre>
    {{ comment }}
    </pre
      >
    </div>
    <div>
      <div
        class="col-6 mb-2 float-left px-2"
        v-for="button in setting.buttons"
        v-bind:key="button.type"
      >
        <button
          type="button"
          v-bind:style="{
            color: button.color,
            'background-color': button.bgColor
          }"
          class="col-12 btn btn-stamp btn-secondary "
        >
          {{ button.displayText | i18n }}
        </button>
      </div>
    </div>
  </div>
</template>
