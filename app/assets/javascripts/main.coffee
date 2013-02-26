$ ->
    $('.delete-class').click ->
        $li = $(this).parents('li')
        todoId = $li.data('todo-id')
        jsRoutes.controllers.ToDos.delete(todoId).ajax
            success: ->
                $li.fadeOut
                    complete: ->
                        $li.remove()
            error: (err) ->
                $.error("Error: " + err)