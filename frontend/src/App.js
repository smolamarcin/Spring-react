import React, { Component } from 'react';
import './App.css';

class TodoItem extends Component {
    static defaultProps = {
        done: false
    }
    state = {
        done: this.props.done,
    }

    toggleDone = () => {
        this.setState({ done: !this.state.done })
    }
    render() {
        // const { text } = this.props.t;
        return (
            <div onClick={this.toggleDone} className={this.state.done ? 'doneTodo' : ''}>{this.props.text}</div>
        );
    }
}
class TodoList extends Component {
    static defaultProps = {
        tasks: [
            { done: true, text: 'first task' },
            { done: false, text: 'second task' }
        ]
    }
    state = {
        tasks: this.props.tasks,
        draft: ''
    }

    addTask = () => {
        // const {tasks, draft} = this.state
        // const list = tasks
        // list.push({text: draft})
        // this.setState({tasks: list, draft: ''})
        this.setState(prevState => (
            {
                tasks: [...prevState.tasks,
                { text: this.state.draft }],
                draft: ""
            })
        )
    }
    updateDraft = (event) => this.setState({ draft: event.target.value })
    render() {
        return (
            <div>
                <h1>{this.props.title}</h1>
                {this.state.tasks.map(task => <TodoItem text={task.text} done={task.done}></TodoItem>)}
                <input type="text" onChange={this.updateDraft} value={this.state.draft}></input>
                <button onClick={this.addTask}>Add</button>
            </div>

        );
    }
}

class App extends Component {

    render() {
        return (
            <div>
                <TodoList />
            </div>
        );
    }
};
export default App;
