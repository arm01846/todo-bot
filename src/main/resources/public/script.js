window.onload = () => liff.init(
  data => {
    const userId = data.context.userId;
    $(() => {
      let template = $('#template').html();
      let outputLocation = $("#output");

      let page = new TodoPage(template, outputLocation);
      $.get("/api/" + userId).then((data) => {
        page.setData(data)
      });

      $(document).on('click', '.important-button', (event) => {
        const todoId = event.target.getAttribute('data-id');
        $.ajax("/api/" + userId + "/" + todoId + "/important", {
          method: 'PUT'
        }).then((data) => {
            page.setData(data);
          });
      });

      $(document).on('click', '.finished-checkbox', (event) => {
        const todoId = event.target.getAttribute('data-id');
        $.ajax("/api/" + userId + "/" + todoId + "/finished", {
          method: 'PUT'
        }).then((data) => {
            page.setData(data);
          });
      });
    });
  },
  err => {}
);

class TodoPage {
  constructor(template, outputLocation) {
    this.template = template;
    this.outputLocation = outputLocation;
    this.data = [];

    Mustache.parse(this.template);
  }

  setData(data) {
    this.data = data;
    this.render()
  }

  render() {
    let output = [];
    for (let d of this.data) {
      d.class = [
        "todo",
        d.important ? "important": null,
        d.finished ? "finished": null,
      ].join(" ");
      d.checked = d.finished ? "checked": null,
      output.push(Mustache.render(this.template, d))
    }
    this.outputLocation.html(output.join(""))
  }
}