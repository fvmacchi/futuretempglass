var displayedTask = "";

window.onload = function()
{
	tasks = document.getElementsByClassName("taskListPanel");
	for(var i = 0; i < tasks.length; i++)
	{
		tasks[i].setAttribute("onclick", "javascript: displayTask('"+ tasks[i].id.substring(5) + "');");
	}
	$("#deleteButton").click(deleteSelectedTask);
}

var deleteSelectedTask = function()
{
	if(displayedTask != "")
	{
		deleteTask(displayedTask);
	}
}

var displayTask = function(taskId)
{
	displayedTask = taskId;

	$(".selectedTaskTools").show();

	selectedTask = document.getElementById("selectedTask");

	taskPanels = $(".taskListPanel");
	taskPanels.removeClass("active");

	clickedTask = $("#TASK-" + taskId);
	selectedTask.innerHTML = clickedTask.html();
	clickedTask.addClass("active");
}

var submitTaskForm = function()
{
	console.log("before submit");
	$("#taskForm").ajaxSubmit({url:"", success: function()
	{
		console.log("after response");
		location.reload();
	}});
	console.log("after submit");
	$("#editTaskModal").modal("hide");
}

var deleteTask = function(taskId)
{
	request = getRequest("DELETE", window.location.origin + "/tasks");
	request.onreadystatechange = function()
  	{
  		if (request.readyState==4 && request.status==200)
  		{
  			responseText = request.responseText;
  			console.log("Response text: " + responseText);
  			location.reload();
  		}
  	}
	request.send(taskId);
}

var openCreateNewTaskModal = function()
{
	$("#editTaskModalTitle").html("Create New Task");
	$("#modalTaskTitle").val("");
	$("#modalTaskAssignee").val("");
	$("#modalTaskDescription").val("");
	$("#editTaskModal").modal("show");
}

var openEditTaskModal = function(taskId)
{

}

var getTaskInfo = function(taskId)
{

}